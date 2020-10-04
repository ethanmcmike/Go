package ethanmcmike.go.meshes;

import javax.microedition.khronos.opengles.GL10;

public class Tile extends Mesh {

    private static final float RADIUS = 0.75f;
    private static final int H_DIV = 20;
    private static final int V_DIV = 10;

    public Tile(){
        initVertices();
        initIndices();

        setColor(0, 0, 1, 0);
    }

    @Override
    protected void initVertices() {

        float[] vertices = new float[H_DIV * V_DIV * 3];
        int index = 0;

        float az, ze;

        for(int i=0; i<H_DIV; i++){
            for(int j=0; j<V_DIV; j++){
                az = (float)(i * Math.PI / H_DIV);
                ze = (float)(j * Math.PI / 2f / V_DIV);

                float x = (float)(RADIUS * Math.cos(az) * Math.sin(ze));
                float y = (float)(RADIUS * Math.cos(ze));
                float z = (float)(RADIUS * Math.sin(az) * Math.sin(ze));

                vertices[index + 0] = x;
                vertices[index + 1] = y;
                vertices[index + 2] = z;
                index += 3;
            }
        }

        setVertices(vertices);
    }

    @Override
    protected void initIndices() {

        short[] indices = new short[H_DIV * V_DIV];

        for(int i=0; i<indices.length; i++){
            indices[i] = (short)i;
        }

        setIndices(indices);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glFrontFace(GL10.GL_CCW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, verticesBuffer);

        gl.glPushMatrix();

//        gl.glTranslatef(x, y, z);
//        gl.glRotatef(rx, 1, 0, 0);
//        gl.glRotatef(ry, 0, 1, 0);
//        gl.glRotatef(rz, 0, 0, 1);

        gl.glColor4f(rgba[0], rgba[1], rgba[2], rgba[3]);

        gl.glDrawElements(GL10.GL_LINE_LOOP, numOfIndices, GL10.GL_UNSIGNED_SHORT, indicesBuffer);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisable(GL10.GL_CULL_FACE);

        gl.glPopMatrix();
    }
}
