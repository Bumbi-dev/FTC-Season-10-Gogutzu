package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="...", group="Bubu")
public class Autonomie_preload extends AutonomHardware {


    @Override
    public void runOpMode() {

        ElapsedTime runtime = new ElapsedTime();

        int nr = 1;

        init(hardwareMap);

        telemetry.addLine(motorFS.getCurrentPosition() + " " + motorFD.getCurrentPosition() + "\n" + motorSS.getCurrentPosition() + " " + motorSD.getCurrentPosition());

        waitForStart();

        runtime.reset();
        while (opModeIsActive()) {

            if(gamepad1.right_bumper || gamepad1.left_bumper)
                return;

            nr -= gamepad1.right_stick_y;

            telemetry.addLine(nr + "");
            telemetry.update();

            if(gamepad1.a) {
                goTo(nr);
            }
            for (DcMotor motor: motoare)
                motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void goTo(int x) {
        try {
            for (DcMotor motor: motoare) {
                motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                motor.setTargetPosition(x);
            }

        } catch (Exception e) {
            telemetry.addLine("nu merge");
            telemetry.update();
        }
    }
}
