package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="...", group="Bubu")
public class Autonomie_preload extends LinearOpMode {

    RobotHardware robot = new RobotHardware();
    Float nr = 0f;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            if(nr < 1) {
                robot.motorFS.setPower(0.1); robot.motorFD.setPower(0.1);
                robot.motorSS.setPower(0.1); robot.motorSD.setPower(0.1);
            }

            nr++;
        }
    }
}
