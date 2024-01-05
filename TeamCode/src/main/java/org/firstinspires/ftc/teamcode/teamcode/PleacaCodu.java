package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "GAMEPLAY", group = "Bubu")
public class PleacaCodu extends RobotHardware{

    boolean sePrinde = false;

    @Override
    public void runOpMode() {
        init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            //Brat: left stick - brat motor,  a-inchide gheara,  b-deschide gheara,  ps-arunca avion,  ps+left+right bumper - cancel
            moveArm();
            moveServos();

            //Roti: a-frana    dpad-miscari drepte,     rt/lt - fata spate, left stick - rotatie,  right stick - strafe
            if(gamepad1.a) {
                frana();
                continue;
            }

            if(straightMove())
                continue;
            sergiuMovevement();
        }
    }

    private void moveArm() {//y blocks the arm for hanging, x disables it
        if(gamepad2.y)
            sePrinde = true;

        if(gamepad2.x)
            sePrinde = false;

        if (sePrinde)
            motorBrat.setPower(0.1);
        else
            motorBrat.setPower(gamepad2.left_stick_y);
    }
    private void moveServos() {
        if(Math.abs(gamepad2.right_stick_y) > 0.1)//move claw with right stick
            setServoPosition((float) ((ghearaStanga.getPosition() + ghearaDreapta.getPosition())/2.0) + gamepad2.right_stick_y / 100);

        if(gamepad2.a)//prinde
            setServoPosition(0);
        if(gamepad2.b)//drop
            setServoPosition(0.25f);

        /*_____  9/11  _____*/
        if(gamepad2.ps)
            if(gamepad2.left_bumper && gamepad2.right_bumper)
                diana.setPosition(1);
            else
                openCapcana();

        if(gamepad2.dpad_down)
            closeCapcana();
    }

    private void sergiuMovevement() {//miscare din joystick
        float y;
        float x;
        float fd, fs, sd, ss;
        float rotatie;

        y = gamepad1.right_trigger - gamepad1.left_trigger;
        rotatie = gamepad1.right_stick_x;
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

        if(gamepad1.b) {
            fs /= 2; fd /= 2;
            ss /= 2; sd /= 2;
        }

        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);
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