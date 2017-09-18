/*
 * Developed by Alessio Sardaro .
 * using javafx layer coding game all right reserved.
 * 
 */
package jeu.patrouille.fx.animation;

import jeu.patrouille.coeur.equipments.GeneriqueEquipment;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme.FeuMode;
import jeu.patrouille.coeur.equipments.armes.GeneriqueArme;
import jeu.patrouille.fx.board.FXCarte;
import jeu.patrouille.fx.sprite.Sprite;

/**
 *
 * @author appleale
 */
public class JeuPatrouilleAnimationTimer extends FrameAnimationTimer{
    GeneriqueArme arme;
    FXCarte fxcarte;    
    Sound type;
    public enum Sound{MARCHE("goodStep.wav"),COURS("run.wav"),MP5("mp5.wav"),M16("m16.wav","m16.wav","M16A4Burst.mp3","fire5.wav"),GRUNT5("grunt2.wav"), 
    AK74("AK47.mp3","AK47.mp3","ak47burst.wav","AK47Auto.mp3"),BENELLI_M3("ShotGunFire.wav"); 
        String files[]=new String[4];
        String file;
        Sound(String file1,String file2,String file3,String file4){
            this.files[FeuMode.SA.ordinal()]=file1;
            this.files[FeuMode.SC.ordinal()]=file2;
            this.files[FeuMode.RA.ordinal()]=file3; 
            this.files[FeuMode.PA.ordinal()]=file4;
            this.file=file1;
        }
        Sound(String file){
         this.file=file;
        }
        public String file(FeuMode m){
            if(files[m.ordinal()]==null) return file;
        return files[m.ordinal()];
        }
        public String file(){
        
        return file;
        }
    }    
    public JeuPatrouilleAnimationTimer(int f[],Sprite sp,double frac,int cyclic,int interval,GeneriqueArme arme,Sound sound) {
        super(f, sp, frac, cyclic, interval, null);
        this.arme=arme;
        this.type=sound;
        if(arme!=null){
            if(arme.getArmeFeuMode()==FeuMode.RA ) cyclic=5;
            else 
             if( arme.getArmeFeuMode()==FeuMode.PA)
               this.ciclyc=10;
             else cyclic=2;
            if(arme.getModel()==GeneriqueEquipment.Model.M16 
                    ) this.sound=Sound.M16.file(arme.getArmeFeuMode());
            if(arme.getModel()==GeneriqueEquipment.Model.AK74) this.sound=Sound.AK74.file(arme.getArmeFeuMode());
            if(arme.getModel()==GeneriqueEquipment.Model.BENELLI_M3) this.sound=Sound.BENELLI_M3.file(arme.getArmeFeuMode());
            if(arme.getModel()==GeneriqueEquipment.Model.MP5) this.sound=Sound.MP5.file(arme.getArmeFeuMode());        
        }else {
            this.sound=type.file;
        
        }
        
    }
    
    
    @Override
    void playEffect(){
        

           
           
           
       if(!mediaPlayer.isPlaying())  {
       if(this.type==Sound.MARCHE) {
               mediaPlayer.setVolume(0.1);
               mediaPlayer.setRate(0.8);
               mediaPlayer.setCycleCount(1);
           }else if(this.type==Sound.COURS){
               mediaPlayer.setRate(1);
               mediaPlayer.setVolume(0.2);
              // mediaPlayer.setCycleCount(3);
           }
           else  if(this.type==Sound.GRUNT5){
               mediaPlayer.setVolume(0.1);
               mediaPlayer.setCycleCount(1);
           }           
           if (null != arme) {
               switch (arme.getModel()) {
                   case MP5:
                       mediaPlayer.setVolume(0.1);
                       break;
                   case AK74:
                       mediaPlayer.setVolume(0.1);
                       break;
                   case M16:
                       mediaPlayer.setVolume(0.1);
                       break;
                   default:
                       break;
               }
               if (arme.getArmeFeuMode() == FeuMode.SA) {
                   mediaPlayer.setCycleCount(1);
               } else {
                   mediaPlayer.setCycleCount(1);
               }
           }

           mediaPlayer.play();
       
        
        }
           
       
        
    }   
    
    public void start(FXCarte fxcarte) {
        super.start();
        this.i=f1;
        this.fxcarte=fxcarte;
        // //To change body of generated methods, choose Tools | Templates.
        
    }    
    
     @Override
     public void stop() {
       super.stop();
       if(fxcarte!=null)fxcarte.setAnimOn(false);
    }    
    
}
