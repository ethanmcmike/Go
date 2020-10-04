package ethanmcmike.go.renderers;

import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ethanmcmike.go.meshes.Sphere;
import ethanmcmike.go.meshes.Tile;

public class BoardRenderer extends GLRenderer {

    Tile tile;
    Sphere sphere;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        super.onSurfaceCreated(gl, config);
        tile = new Tile();
        sphere = new Sphere(5, 10, 10);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        super.onDrawFrame(gl);

        gl.glLoadIdentity();

        GLU.gluLookAt(gl, 0, 0, 20, 0, 0, 0, 0, 1, 0);

        tile.draw(gl);
        sphere.draw(gl);
    }
}
