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

        boolean omni = false;

        while (opModeIsActive()) {
            if(gamepad1.left_bumper || gamepad1.right_bumper)
                return;


            if(gamepad1.right_stick_button)
                omni = !omni;

            if(omni) {
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
            y = 0.5f;

        if(gamepad1.dpad_down)
            y -= 0.5f;

        if(gamepad1.dpad_right)
            x = 0.5f;

        if(gamepad1.dpad_left)
            x -= 0.5f;

        fs = y + x; fd = y - x;// ar trebui injumatatite diagonalele cand merge pe diagonala
        ss = y - x; sd = y + x;


        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);
    }

    void omniDirectionalMove(){
        float y;
        float x;
        float fd, fs, sd, ss;

        y = -gamepad1.left_stick_y / 2;
        x = gamepad1.left_stick_x / 2;

        fs = y + x; fd = y - x;// ar trebui injumatatite diagonalele cand merge pe diagonala
        ss = y - x; sd = y + x;


        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);
    }

    public void setDiagonal(float x, float y) {
        motorFS.setPower(x); motorFD.setPower(y);
        motorSS.setPower(y); motorSD.setPower(x);
    }
}
