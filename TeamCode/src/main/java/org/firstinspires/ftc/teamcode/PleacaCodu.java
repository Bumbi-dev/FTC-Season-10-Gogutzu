package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "vruuuuum", group = "Bubu")
public class PleacaCodu extends RobotHardware{

    @Override
    public void runOpMode() {

        init(hardwareMap);

        telemetry.addLine("Dai drumu"); telemetry.update();

        waitForStart();

        telemetry.update();

        while (opModeIsActive()) {
            if(gamepad1.start)
                return;

            if(gamepad1.a) {
                frana();
                continue;
            }

            if(straightMove())
                continue;

            omniMovevement();
        }
    }

    private void omniMovevement(){
        float y;
        float x;
        float fd, fs, sd, ss;
        float rotatie;

        rotatie = gamepad1.right_trigger - gamepad1.left_trigger;
        y = -gamepad1.left_stick_y;
        x = gamepad1.left_stick_x;

        fs = y + x; fd = y - x;
        ss = y - x; sd = y + x;

        if(rotatie > 0) {
            fs += rotatie;   fd -= rotatie;
            ss += rotatie;   sd -= rotatie;
        } else if(rotatie < 0) {
            fd -= rotatie;   fd += rotatie;
            sd -= rotatie;   sd += rotatie;
        }

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


    private boolean straightMove() {
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

        if(fs == 0 && fd == 0 && ss == 0 && sd == 0)
            return false;

        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);

        return true;
    }
}
