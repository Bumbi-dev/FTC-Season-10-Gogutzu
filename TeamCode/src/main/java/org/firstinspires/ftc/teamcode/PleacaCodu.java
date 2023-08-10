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


            if(gamepad1.right_stick_button) {
                omniDirectionalMove();
                continue;
            }

            straightMove();
        }

    }

    void straightMove() {
        //Rotation mode
        float rotatie = gamepad1.right_trigger - gamepad1.left_trigger;

        if(rotatie != 0) {
            beyBlade(rotatie);
            return;
        }
        //Navigation mode
        float y = 0;
        float x = 0;
        float fd, fs, sd, ss;

        if(gamepad1.dpad_up)
            y = 1;

        if(gamepad1.dpad_down)
            y -= 1;

        if(gamepad1.dpad_right)
            x = 1;

        if(gamepad1.dpad_left)
            x -= 1;

        fs = y + x; fd = y - x;// ar trebui injumatatite diagonalele cand merge pe diagonala
        ss = y - x; sd = y + x;


        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);
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
