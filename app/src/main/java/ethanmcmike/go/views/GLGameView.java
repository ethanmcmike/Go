package ethanmcmike.go.views;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import ethanmcmike.go.renderers.GLRenderer;

public class GLGameView extends GLSurfaceView {

    private GLRenderer renderer;

    public GLGameView(Context context) {
        super(context);
        init();
    }

    public GLGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        renderer = new GLRenderer();
        setRenderer(renderer);
        setRenderMode(RENDERMODE_CONTINUOUSLY);
    }
}
