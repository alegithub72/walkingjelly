/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeu.patrouille.fx.sprite;

import javafx.beans.value.WritableIntegerValue;
import javafx.geometry.Rectangle2D;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import jeu.patrouille.fx.board.FXCarte;

/**
 *
 * @author appleale
 */
public class Sprite extends Parent implements WritableIntegerValue {

    protected ImageView imgMainView;

    public static int MOVE_TRANSITION = 0;
    Image imgMain;
    Sprite[] extraSprite = new Sprite[2];
    Rectangle2D[] frames;
    int w;
    int h;

    protected int defaultFrame;
    int k;
    double x, y;


    public Sprite(int w, int h, String img) {
        super();
        this.w = w;
        this.h = h;


        if (img != null) {
            this.imgMain = new Image(img);
        }


    }

    public void buildFrameImages(Image frameImages) {
        if (imgMainView != null) {
            getChildren().remove(imgMainView);
        }
        this.imgMain = frameImages;
        createShapeFrame();
        imgMainView = new ImageView(frameImages);
        imgMainView.setViewport(frames[0]);

        if (!getChildren().contains(imgMainView)) {
            getChildren().add(imgMainView);
        }

    }

    protected void buildFrameImages() {

        createShapeFrame();
        imgMainView = new ImageView(imgMain);
        imgMainView.setViewport(frames[0]);
        getChildren().add(imgMainView);

    }

    void createShapeFrame() {
        int n = (imgMain.widthProperty().intValue() / w);
        frames = new Rectangle2D[n];
        for (int i = 0; i < n; i++) {
            frames[i] = new Rectangle2D(i * w, 0, w, h);
        }
    }

    public Node getImgMainView() {
        return imgMainView;
    }

    public Image getImgMain() {
        return imgMain;
    }



    public int getK() {
        return k;
    }

    public void setK(int k) {
        this.k = k;
    }

    public void setFrame(int i) {
        if (i >= frames.length) {
            i = 0;
        }
        this.defaultFrame = i;
        imgMainView.setViewport(frames[i]);
    }



    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public static double convertBoardIposition(int i, int w) {

        double x = ((i * w) + (w / 2))
                - (FXCarte.TILE_SIZE / 2);
        return x;
    }

    public static double convertBoardJposition(int j, int h) {
        double y = ((j * h) + (h / 2))
                - (FXCarte.TILE_SIZE / 2);
        return y;
    }

    public static double convertBoardIpositionCenter(int i, int w) {

        double x = ((i * w)
                + (w / 2));
        return x;
    }

    public static double convertBoardJpositionCenter(int j, int h) {

        double y = ((j * h)
                + (h / 2));

        return y;
    }

    public void removeThis() {
        //fbx.remove(this);
    }

//TODO permette la costruzioni di varie immagini....    
    public Image composedImage(String f) {
        ImageView test = new ImageView(new Image("menuItem.png"));
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        Image rotatedImage = test.snapshot(params, null);
        return rotatedImage;

    }

    public void defaultFrame() {
        setFrame(defaultFrame);
    }

    public void setDeafultFrame(int n) {
        this.defaultFrame = n;
    }

    @Override
    public int get() {
        return defaultFrame;
    }

    @Override
    public void set(int value) {
        setDeafultFrame(value);
    }

    @Override
    public void setValue(Number value) {
        setDeafultFrame(value.intValue());
    }

    @Override
    public Number getValue() {
        return defaultFrame;
    }

}
