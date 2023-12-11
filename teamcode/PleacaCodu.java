package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "GAMEPLAY", group = "Bubu")
public class PleacaCodu extends RobotHardware{

    @Override
    public void runOpMode() {

        init(hardwareMap);

        telemetry.addLine("Dai drumu"); telemetry.update();

        waitForStart();

        telemetry.update();//nu se mai afiseaza dai drumu

        boolean dublu = false;

        while (opModeIsActive()) {
            //Brat
            motorBrat.setPower(gamepad2.left_stick_y);

            setServoPosition(gamepad2.right_stick_y);

            telemetry.addLine(ghearaDreapta.getPosition() + "");
            telemetry.addLine(ghearaStanga.getPosition() + "");
            telemetry.update();


            //Roti
            if(gamepad2.ps)//se opreste daca apesi pe start
                requestOpModeStop();

            if(gamepad1.a) {
                frana();
                continue;
            }

            //caterinca
            if(gamepad1.left_bumper)
                dublu = true;

            if(gamepad1.right_bumper)
                dublu = false;
            //

            if(dublu) {
                doubleMovement();
                continue;
            }

            if(straightMove())
                continue;

            omniMovevement();
        }
    }

    private void omniMovevement(){//miscare din joystick
        float y;
        float x;
        float fd, fs, sd, ss;
        float rotatie;

        rotatie = gamepad1.right_trigger - gamepad1.left_trigger;
        y = -gamepad1.left_stick_y;
        x = gamepad1.left_stick_x;

        fs = y + x; fd = y - x;
        ss = y - x; sd = y + x;

        fs += rotatie;   fd -= rotatie;
        ss += rotatie;   sd -= rotatie;

        //vezi daca merge
        //max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        //max = Math.max(max, Math.abs(leftBackPower));
        //max = Math.max(max, Math.abs(rightBackPower));

        //if (max > 1.0) {
        //    leftFrontPower  /= max;
        //    rightFrontPower /= max;
        //    leftBackPower   /= max;
        //    rightBackPower  /= max;
        //}

        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);
    }

    //caterinca
    private void doubleMovement() {
        motorFS.setPower(-gamepad1.left_stick_y); motorFD.setPower(-gamepad1.right_stick_y);
        motorSS.setPower(-gamepad2.left_stick_y); motorSD.setPower(-gamepad2.right_stick_y);
    }

    private boolean straightMove() {//miscare din dpad
        //Navigation mode
        float y = 0;
        float x = 0;
        float fd, fs, sd, ss;

        if(gamepad1.dpad_up)
            y = 0.5f;

        if(gamepad1.dpad_down)
            y -= 0.5f;

        if(gamepad1.dpad_right)
            x = 0.5f;

        if(gamepad1.dpad_left)
            x -= 0.5f;

        fs = y + x; fd = y - x;
        ss = y - x; sd = y + x;

        if(fs == 0 && fd == 0 && ss == 0 && sd == 0)//daca nu sa apasat nimic pe dpad returneaza fals
            return false;

        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);

        return true;
    }
}
