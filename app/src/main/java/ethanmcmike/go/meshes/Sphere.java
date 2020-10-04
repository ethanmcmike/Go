package ethanmcmike.go.meshes;

import javax.microedition.khronos.opengles.GL10;

public class Sphere extends Mesh {

    public float r;
    public int hDiv, vDiv;

    public Sphere(float r, int hDiv, int vDiv){
        super();

        this.r = r;
        this.hDiv = hDiv;
        this.vDiv = vDiv;

        initVertices();
        initIndices();

        setColor(1, 1, 1, 0);
    }

    @Override
    protected void initVertices() {

        float[] vertices = new float[hDiv*vDiv*3];
        int index = 0;

        for(int i=0; i<vDiv; i++){
            for(int j=0; j<hDiv; j++){
                float zenuth = (float)(i * Math.PI / vDiv);
                float azimuth = (float)(j * 2*Math.PI / hDiv);

                vertices[index + 0] = (float)(r * Math.sin(azimuth) * Math.sin(zenuth));
                vertices[index + 1] = (float)(r * Math.cos(zenuth));
                vertices[index + 2] = (float)(r * Math.cos(azimuth) * Math.sin(zenuth));
                index += 3;
            }
        }

        //Poles
//        vertices[index + 0] = 0;
//        vertices[index + 1] = r;
//        vertices[index + 2] = 0;
//        vertices[index + 3] = 0;
//        vertices[index + 4] = -r;
//        vertices[index + 5] = 0;

        setVertices(vertices);
    }

    @Override
    protected void initIndices() {

        short[] indices = new short[hDiv*vDiv];

        for(short i=0; i<indices.length; i++){
            indices[i] = i;
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
