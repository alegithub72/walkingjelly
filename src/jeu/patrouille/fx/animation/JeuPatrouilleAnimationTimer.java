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
    public enum Sound{MARCHE("goodStep.wav"),MP5("mp5.wav"),M16("m16.wav"),GRUNT5("grunt2.wav"), 
    AK74("AK47.mp3"),BENELLI_M3("ShotGunFire.wav"); 
        String file;
        Sound(String file){
        this.file=file;
        }
        public String file(){
        return file;
        }
    }    
    public JeuPatrouilleAnimationTimer(int f1,int f2,Sprite sp,double frac,int cyclic,int interval,GeneriqueArme arme,Sound sound) {
        super(f1, f2, sp, frac, cyclic, interval, (sound!=null)?sound.file():null);
        this.arme=arme;
        this.type=sound;
        if(arme!=null){
            if(arme.getModel()==GeneriqueEquipment.Model.M16 
                    ) this.sound=Sound.M16.file();
            if(arme.getModel()==GeneriqueEquipment.Model.AK74) this.sound=Sound.AK74.file();
            if(arme.getModel()==GeneriqueEquipment.Model.BENELLI_M3) this.sound=Sound.BENELLI_M3.file();
            if(arme.getModel()==GeneriqueEquipment.Model.MP5) this.sound=Sound.MP5.file();        
        }
        
    }
    
    
    @Override
    void playEffect(){
        

           
           
           
       if(!mediaPlayer.isPlaying())  {
       if(this.type==Sound.MARCHE) {
               mediaPlayer.setVolume(0.1);
               mediaPlayer.setRate(0.8);
               mediaPlayer.setCycleCount(1);
           }else if(this.type==Sound.GRUNT5){
               mediaPlayer.setVolume(0.1);

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
               if (arme.getArmeFeuModel() == FeuMode.SA) {
                   mediaPlayer.setCycleCount(3);
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
