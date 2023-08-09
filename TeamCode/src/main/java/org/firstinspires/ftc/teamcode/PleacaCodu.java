package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="vruuuuum", group="Bubu")
public class PleacaCodu extends LinearOpMode {

    RobotHardware robot = new RobotHardware();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        telemetry.addLine("Dai drumu"); telemetry.update();
        
        waitForStart();

        while (opModeIsActive()) {

            if(gamepad1.left_bumper || gamepad1.right_bumper)
                return;

            if(gamepad1.right_stick_button) {
                omniDirectionalMove();
            }

            straightMove();

        }

    }

    void straightMove() {

        float y = 0;
        float x = 0;

        if(gamepad1.dpad_up)
            y += 0.2f;

        if(gamepad1.dpad_down)
            y -= 0.2f;

        if(gamepad1.dpad_right)
            x += 0.2f;

        if(gamepad1.dpad_left)
            x -= 0.2f;

        if(y != 0) {//pt cv omni - directional float putere, putere = y, putere -= x sau ceva. pt diagonala

            if(x != 0)
                if (x > 0) {
                    if(y > 0)
                        robot.moveDiagonal(y, "FD");
                    else
                        robot.moveDiagonal(y, "FS");
                }

                else
                    if(y > 0)
                        robot.moveDiagonal(y, "FS");
                    else
                        robot.moveDiagonal(y, "FD");
            else
                robot.moveStraight(y);
            return;
        }

        if(x != 0) {
            robot.moveStrafe(-x);
            return;
        }

        float rotatie = gamepad1.right_trigger - gamepad1.left_trigger;

        if(rotatie != 0) {
            robot.beyBlade(rotatie / 2);
            return;
        }

        robot.stop();

    }

    void omniDirectionalMove(){
        float upDown = -gamepad1.left_stick_y;
        float leftRight = gamepad1.left_stick_x;

        if(upDown > 0.20 || leftRight > 0.20)
            setDiagonal(upDown, upDown - leftRight);
        else robot.stop();
    }

    public void setDiagonal(float x, float y) {
        robot.motorFS.setPower(x); robot.motorFD.setPower(y);
        robot.motorSS.setPower(y); robot.motorSD.setPower(x);
    }
}
