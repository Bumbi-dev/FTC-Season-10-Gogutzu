package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="vruuuuum", group="Bubu")
public class PleacaCodu extends RobotHardware{

    @Override
    public void runOpMode() {

        init(hardwareMap);

        telemetry.addLine("Dai drumu"); telemetry.update();
        
        waitForStart();

        while (opModeIsActive()) {
            if(gamepad1.left_bumper || gamepad1.right_bumper)
                return;

            motorsTelemetry(telemetry);


            if(gamepad1.right_stick_button) {
                omniDirectionalMove();
                continue;
            }

            straightMove();
        }

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

        fs = y; fd = y;
        ss = y; sd = y;

        fs += x; fd -= x;
        ss -= x; sd += x;


        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);


        //Rotation mode
        float rotatie = gamepad1.right_trigger - gamepad1.left_trigger;

        if(rotatie != 0)
            beyBlade(rotatie / 2);
    }

    void omniDirectionalMove(){
        float upDown = -gamepad1.left_stick_y;
        float leftRight = gamepad1.left_stick_x;

        if(upDown > 0.20 || leftRight > 0.20)
            setDiagonal(upDown, upDown - leftRight);
        else stop();
    }

    public void setDiagonal(float x, float y) {
        motorFS.setPower(x); motorFD.setPower(y);
        motorSS.setPower(y); motorSD.setPower(x);
    }
}
