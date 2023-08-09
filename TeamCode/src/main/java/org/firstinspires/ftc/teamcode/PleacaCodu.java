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

            motorsTelemetry(telemetry);

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

        float y = 0;
        float x = 0;

        if(gamepad1.dpad_up)
            y = 0.5f;

        if(gamepad1.dpad_down)
            y -= 0.5f;

        if(gamepad1.dpad_right)
            x = 0.5f;

        if(gamepad1.dpad_left)
            x -= 0.5f;
        
        
        if(y != 0) {//pt cv omni - directional float putere, putere = y, putere -= x sau ceva. pt diagonala
            if(x != 0)
                if (x > 0) {
                    if(y > 0)
                        moveDiagonal(y, "FD");
                    else
                        moveDiagonal(y, "FS");
                }

                else
                    if(y > 0)
                        moveDiagonal(y, "FS");
                    else
                        moveDiagonal(y, "FD");
            else
                moveStraight(y);
            return;
        }

        if(x != 0) {
            moveStrafe(-x);
            return;
        }

        float rotatie = gamepad1.right_trigger - gamepad1.left_trigger;

        if(rotatie != 0) {
            beyBlade(rotatie / 2);
            return;
        }

        gata();

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
