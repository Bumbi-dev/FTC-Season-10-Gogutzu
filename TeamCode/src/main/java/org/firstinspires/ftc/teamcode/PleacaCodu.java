package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "vruuuuum", group = "Bubu")
public class PleacaCodu extends RobotHardware{

    @Override
    public void runOpMode() {

        init(hardwareMap);

        telemetry.addLine("Dai drumu"); telemetry.update();

        ElapsedTime runtime = new ElapsedTime();

        boolean omni = false;

        waitForStart();

        while (opModeIsActive()) {
            if(gamepad1.left_bumper || gamepad1.right_bumper)
                return;

            //Rotation
            float rotatie = gamepad1.right_trigger - gamepad1.left_trigger;//in omni direction sa adauge putere pt pareta in care se roteste

            if(rotatie != 0) {
                beyBlade(rotatie);
                continue;
            }

            if (gamepad1.right_stick_button && runtime.milliseconds() >= 0.2) {//asteapta 0.2 secunde intre schimbari
                omni = !omni;
                runtime.reset();
            }

            if(omni) {
                omniDirectionalMove();
                continue;
            }

            straightMove();
        }
    }

    void omniDirectionalMove(){
        float y;
        float x;
        float fd, fs, sd, ss;

        y = -gamepad1.left_stick_y;
        x = gamepad1.left_stick_x;

        fs = y + x; fd = y - x;
        ss = y - x; sd = y + x;

        telemetry.addLine(fs + " " + fd + '\n' +
                                   ss + " " + sd);

        telemetry.update();

        //max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        //max = Math.max(max, Math.abs(leftBackPower));
        //max = Math.max(max, Math.abs(rightBackPower));

        //if (max > 1.0) {
        //    leftFrontPower  /= max;
        //    rightFrontPower /= max;
        //    leftBackPower   /= max;
        //    rightBackPower  /= max;
        //}

        //motorFS.setPower(fs);  motorFD.setPower(fd);
        //motorSS.setPower(ss);  motorSD.setPower(sd);
    }


    void straightMove() {
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


        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);
    }
}
