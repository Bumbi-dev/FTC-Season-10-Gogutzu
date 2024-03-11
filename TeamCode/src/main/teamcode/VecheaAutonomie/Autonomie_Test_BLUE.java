package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="TEST-BLUE! (play)", group="zyzz")
public class Autonomie_Test_BLUE extends PlayHardware {
    @Override
    public void runOpMode() {
        path += "_blue_test";

        init(hardwareMap);
    }
}