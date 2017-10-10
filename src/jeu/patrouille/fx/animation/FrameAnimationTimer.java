/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.animation;

import java.net.URL;
import javafx.animation.AnimationTimer;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;
import jeu.patrouille.fx.sprite.Sprite;



/**
 *
 * @author appleale
 */
public class FrameAnimationTimer extends AnimationTimer{
    

    int f1,f2;
    int f[];
    public String sound;
    public Sprite source;
    public Sprite shot;
    public Sprite target;
    public String soundName;
    int i;

    long before;
    long interval=0;  
    AudioClip mediaPlayer;
    Duration half;
    boolean detectionRevelead;
    boolean stopped;
   // private PathTransition pathTransition;
    int loop=0;

    int ciclyc;
    double frac;
    boolean startOne;

    public AudioClip getMediaPlayer() {
        return mediaPlayer;
    }

    
    public FrameAnimationTimer(int[] f, Sprite p,double frac,int cyclic,long interval,String sound) {
        this.f=f;
        target=null;
        shot=null;
        this.source = p;
        this.sound=sound;
        startOne=true;
        //
         this.stopped=false;
        this.frac=frac;
        this.ciclyc=cyclic;
        i=f[0];
        this.interval=interval;
     
    }
    
    
    public FrameAnimationTimer(int f1, int f2, Sprite source,Sprite shot,Sprite target,double frac,int cyclic,long interval,String sound) {
        this.f1 = f1;
        this.f2 = f2;
        this.f=new int[]{f1,f2};
        //System.out.println("target"+target+",source="+source+",shot="+shot);
        this.target=target;
        this.source = source;
        this.sound=sound;
        this.shot=shot;
        startOne=true;

        //

        this.frac=frac;
        this.ciclyc=cyclic;
        i=0;
        this.interval=interval;
        this.stopped=false;
       
    }    

    public boolean isStopped() {
        return stopped;
    }
     
    
    @Override  
    public void handle(long now) {

        long intervalTemp=System.currentTimeMillis()-before;
        playEffect();
        if(i<f.length  && intervalTemp>this.interval) {
            before=System.currentTimeMillis();
            source.setFrame(f[i]);
            i++;
            if(loop<this.ciclyc){
                if(i>=f.length) {
                    i=0;
                    loop++;
                }
                
            }else if(loop>=this.ciclyc && (i>=f.length) ) {
                if(ciclyc!=-1) {
                    source.removeThis();
                    this.stop();
                    source.defaultFrame();
                }
              
                i=0;
            }

        } 
    }

     @Override
     public void stop() {
       super.stop();
       if(mediaPlayer!=null && this.ciclyc==-1)  {
         mediaPlayer.stop();
       }
       System.out.println("stopped animation........");
       stopped=true;

    }

    @Override
    public void start() {
        super.start();
        this.i=f1;
        // //To change body of generated methods, choose Tools | Templates.
        
    }
    


    
    void playEffect(){
        

       if(startOne) {

               mediaPlayer.setVolume(0.1);
               mediaPlayer.setRate(0.8);
               mediaPlayer.setCycleCount(1);
               if(!mediaPlayer.isPlaying()) mediaPlayer.play();
               startOne=false;
           }
     
        
    }
    
    
   public  void buildMedia(){
        ClassLoader classLoader = getClass().getClassLoader();
        URL url=classLoader.getResource(sound);
        mediaPlayer= new AudioClip(url.toString());
                
    }
    
    
}
