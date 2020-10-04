package ethanmcmike.go.utils;

public class Vector3 {

    // X-RIGHT, Y-UP, Z-OUT
    // R-RADIUS, A-ANGLE IN XZ PLANE CLOCKWISE FROM Z_AXIS, B-ANGLE FROM (PERPENDICULAR TO Y_AXIS)(POSITIVE UP)

    public static final Vector3 ZERO = new Vector3();
    public static final Vector3 UNIT_X = new Vector3(1, 0, 0);
    public static final Vector3 UNIT_Y = new Vector3(0, 1, 0);
    public static final Vector3 UNIT_Z = new Vector3(0, 0, 1);

    public float x, y, z;

    public Vector3() {
        x = 0;
        y = 0;
        z = 0;
    }

    public Vector3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Vector3 v) {
        x += v.x;
        y += v.y;
        z += v.z;
    }

    public Vector3 plus(Vector3 v) {
        return new Vector3(x + v.x, y + v.y, z + v.z);
    }

    public void subtract(Vector3 v) {
        x -= v.x;
        y -= v.y;
        z -= v.z;
    }

    public Vector3 minus(Vector3 v) {
        return new Vector3(x - v.x, y - v.y, z - v.z);
    }

    public void mult(float multiplier) {
        x *= multiplier;
        y *= multiplier;
        z *= multiplier;
    }

    public Vector3 times(float multiplier) {
        return new Vector3(x * multiplier, y * multiplier, z * multiplier);
    }

    public Vector3 times(Vector3 multiplier) {
        return new Vector3(x * multiplier.x, y * multiplier.y, z * multiplier.z);
    }

    public Vector3 divide(float divisor) {
        return new Vector3(x / divisor, y / divisor, z / divisor);
    }

    public float magnitude() {
        return (float)Math.sqrt((double)((x*x) + (y*y) + (z*z)));
    }

    public Vector3 toUnit() {
        if(magnitude() == 0)
            return ZERO;
        else
            return divide(magnitude());
    }

    public Vector3 toLength(float len) {
        return toUnit().times(len);
    }

    public float distanceTo(Vector3 v) {
        return v.minus(this).magnitude();
    }

    public Vector3 mod(float mod){
        return new Vector3(x % mod, y % mod, z % mod);
    }

//    public float getAngle() {
//        float angle = (float) ((Math.atan((double) (x / y)) * 180d) / 3.141592653589793d);
//        if (y < 0) {
//            return angle + 180;
//        }
//        if (x < 0 && y > 0) {
//            return angle + 360;
//        }
//        if (x >= 0 && y == 0) {
//            return 90.0f;
//        }
//        if (x > 0 || y != 0) {
//            return angle;
//        }
//        return 270;
//    }

//    public float angleTo(Vector2 v) {
//        return v.minus(this).getAngle();
//    }

    public float dot(Vector3 v) {
        return (x * v.x) + (y * v.y) + (z * v.z);
    }

    public Vector3 cross(Vector3 v){
        Vector3 result = new Vector3();
        result.x = y*v.z - z*v.y;
        result.y = z*v.x - x*v.z;
        result.z = x*v.y - y*v.x;
        return result;
    }

    public Vector3 proj(Vector3 v){
        return toUnit().times(dot(v)).divide(magnitude());
    }

    public Vector3 copy(){
        return new Vector3(x, y, z);
    }

    public static Vector3 toPolar(Vector3 rect){

        float r = rect.magnitude();
        float a = (float)Math.atan(rect.x / rect.z);
        if(rect.z<0)
            a += Math.PI;

        float b = (float)Math.acos(rect.y / r);

        return new Vector3(r, a, b);
    }

    public static Vector3 toRect(Vector3 polar){

        float y = (float)(polar.x * Math.cos(polar.z));
        float z = (float)(polar.x * Math.cos(polar.y) * Math.sin(polar.z));
        float x = (float)(polar.x * Math.sin(polar.y) * Math.sin(polar.z));

        return new Vector3(x, y, z);
    }

//    public Vector3 orth() {
//        return new Vector3(y, -x);
//    }

    public String toString() {
        return "("+x+","+y+","+z+")";
    }
}
