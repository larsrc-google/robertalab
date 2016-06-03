package de.fhg.iais.roberta.ast.syntax.expr;

import org.junit.Test;

import de.fhg.iais.roberta.testutil.Helper;

public class MathConstantTest {
    @Test
    public void Test() throws Exception {

        String a = "PI2.71828((1.0+sqrt(5.0))/2.0)sqrt(2)sqrt(1.0/2.0)0x7f800000";
        //"Float.POSITIVE_INFINITY";

        Helper.assertCodeIsOk(a, "/syntax/math/math_constant.xml");
    }

    //ignore
    public void Test1() throws Exception {

        final String a = "RotateMotor(B,PI,360.0*((1.0+sqrt(5.0))/2.0)))";

        Helper.assertCodeIsOk(a, "/syntax/math/math_constant1.xml");
    }

}
