package ethanmcmike.go.meshes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import ethanmcmike.go.utils.Vector3;

public abstract class Mesh {

    public Vector3 pos, rot;

    protected boolean drawGrid;

    protected FloatBuffer verticesBuffer = null;
    protected ShortBuffer indicesBuffer = null;
    protected FloatBuffer textureBuffer = null;

    protected int numOfIndices = -1;
    protected int[] textures = new int[1];

    protected float[] rgba = {1.0f, 1.0f, 1.0f, 1.0f};
    protected FloatBuffer colorBuffer = null;

    public Mesh(){
        pos = Vector3.ZERO;
        rot = Vector3.ZERO;
    }

    protected abstract void initVertices();

    protected abstract void initIndices();

    public void draw(GL10 gl) {

        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);

        gl.glPushMatrix();

        gl.glTranslatef(pos.x, pos.y, pos.z);
        gl.glRotatef(rot.x, 1, 0, 0);
        gl.glRotatef(rot.y, 0, 1, 0);
        gl.glRotatef(rot.z, 0, 0, 1);

        //Fill color
        gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);

        if ( colorBuffer != null ) {
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
        }

        //Draw square
        gl.glDrawElements(GL10.GL_TRIANGLES, numOfIndices, GL10.GL_UNSIGNED_SHORT, indicesBuffer);

        //Draw outline
        if(drawGrid) {
            //Outline color
            gl.glColor4f(0, 0, 0, 0);
            gl.glLineWidth(2);

            //Draw outline
            gl.glDrawElements(GL10.GL_LINE_STRIP, numOfIndices, GL10.GL_UNSIGNED_SHORT, indicesBuffer);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);

        gl.glPopMatrix();
    }

    protected void setVertices(float[] vertices) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);    //Float is 4 bytes
        byteBuffer.order(ByteOrder.nativeOrder());
        verticesBuffer = byteBuffer.asFloatBuffer();
        verticesBuffer.put(vertices);
        verticesBuffer.position(0);
    }

    protected void setIndices(short[] indices) {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(indices.length * 2);     //Short is 2 bytes
        byteBuffer.order(ByteOrder.nativeOrder());
        indicesBuffer = byteBuffer.asShortBuffer();
        indicesBuffer.put(indices);
        indicesBuffer.position(0);
        numOfIndices = indices.length;
    }

    protected void setTexture(float[] texture){
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
        byteBuffer.order(ByteOrder.nativeOrder());
        textureBuffer = byteBuffer.asFloatBuffer();
        textureBuffer.put(texture);
        textureBuffer.position(0);
    }

    public void setColor(float red, float green, float blue, float alpha) {
        rgba[0] = red;
        rgba[1] = green;
        rgba[2] = blue;
        rgba[3] = alpha;
    }

    public void setColors(float[] colors) {
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    public void setPos(float x, float y, float z){
        pos = new Vector3(x, y, z);
    }

    public void translate(float dx, float dy, float dz){
        pos.x += dx;
        pos.y += dy;
        pos.z += dz;
    }

    public void setRot(float dax, float day, float daz){
        rot.x += dax;
        rot.y += day;
        rot.z += daz;
    }

    public void setDrawGrid(boolean drawGrid){
        this.drawGrid = drawGrid;
    }
}
