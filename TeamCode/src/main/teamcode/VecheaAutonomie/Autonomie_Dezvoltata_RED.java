package org.firstinspires.ftc.teamcode.VecheaAutonomie;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

//MAX VOLTAGE = 14
@Autonomous(name="RED! (play)", group="play")
public class Autonomie_Dezvoltata_RED extends PlayHardware {
    @Override
    public void runOpMode() {
        path += "_red";

        init(hardwareMap);
    }

}