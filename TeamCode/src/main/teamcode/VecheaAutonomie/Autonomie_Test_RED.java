package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//MAX VOLTAGE = 14
@Autonomous(name="TEST-RED! (play)", group="zyzz")
public class Autonomie_Test_RED extends PlayHardware {
    @Override
    public void runOpMode() {
        path += "_red_test";

        init(hardwareMap);
    }

}