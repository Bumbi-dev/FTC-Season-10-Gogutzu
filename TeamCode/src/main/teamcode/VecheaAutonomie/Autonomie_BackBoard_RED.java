package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="BACKBOARD-RED! (play)", group="play")
public class Autonomie_BackBoard_RED extends PlayHardware {
    @Override
    public void runOpMode() {
        path += "_red_backboard";

        init(hardwareMap);
    }
}