package org.firstinspires.ftc.LTASrob;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp(name = "✈️GAMEPLAY✈️", group = "Bubu")
public class PleacaCodu extends RobotHardware{

    boolean sePrinde = false;

    @Override
    public void runOpMode() {
        init(hardwareMap);

        waitForStart();

        while (opModeIsActive() && !isStopRequested()) {
            //Brat: LEFT STICK - brat motor,  A-inchide gheara,  B-deschide gheara,
            //PS-deschide capcana PS+ LEFT+RIGHT BUMPER - arunca avion
            moveArm();
            moveServos();

            //Roti: A-frana DPAD-miscari drepte, RT/LT - fata spate
            //LEFT STICK - rotatie,  RIGHT STICK - strafe
            if(gamepad1.a) {
                frana();
                continue;
            }

            if(straightMove())
                continue;
            sergiuMovevement();
        }
    }

    private void moveArm() {//y blocks the arm for hanging, x disables it
        freeFall();

        if(gamepad2.y)
            sePrinde = true;
        if(gamepad2.x)
            sePrinde = false;

        if(Math.abs(gamepad2.left_stick_y) > 0.1)
            motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (sePrinde)
            motorBrat.setPower(0.1);
        else
            motorBrat.setPower(gamepad2.left_stick_y);

        if(gamepad2.start) {
            motorBrat.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        if(gamepad2.dpad_up)
            ArmToPosition(ArmPositions.BACK_BOARD_POSITION);
        if(gamepad2.dpad_down)
            ArmToPosition(ArmPositions.HOVER_POSITION);
        if(gamepad2.dpad_left)
            ArmToPosition(ArmPositions.STACK_POSITION);

        telemetry.addLine(ghearaDreapta.getPosition() + "" + '\n' + ghearaStanga.getPosition());
        telemetry.addLine(motorBrat.getCurrentPosition() + "");
        telemetry.update();
    }
    private void freeFall() {
        if(gamepad2.option)
            motorBrat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        else
            motorBrat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    private void moveServos() {
        if(Math.abs(gamepad2.right_stick_y) > 0.1)
            if(gamepad2.right_stick_button)
                ghearaStanga.setPosition(ghearaStanga.getPosition() + gamepad2.right_stick_y / 100);
            else
                ghearaDreapta.setPosition(ghearaDreapta.getPosition() + gamepad2.right_stick_y / 100);

        if(gamepad2.left_bumper)
            setLeftServoPosition(ServoPositions.OPEN);
        if(gamepad2.left_trigger > 0.3)
            setLeftServoPosition(ServoPositions.CLOSE);
        if(gamepad2.right_bumper)
            setRightServoPosition(ServoPositions.OPEN);
        if(gamepad2.right_trigger > 0.3)
            setRightServoPosition(ServoPositions.CLOSE);

        /*_____  9/11  _____*/
        if(gamepad2.ps)
            if(gamepad2.back)
                diana.setPosition(1);
            else
                openCapcana();

        if(gamepad2.x)
            closeCapcana();
    }

    private void sergiuMovevement() {//miscare din joystick
        float y;
        float x;
        float fd, fs, sd, ss;
        float rotatie;

        y = gamepad1.right_trigger - gamepad1.left_trigger;
        rotatie = gamepad1.right_stick_x;
        x = gamepad1.left_stick_x;

        fs = y + x; fd = y - x;
        ss = y - x; sd = y + x;

        fs += rotatie;   fd -= rotatie;
        ss += rotatie;   sd -= rotatie;

        //vezi daca merge
        float max;
        max = Math.max(Math.abs(fs), Math.abs(fd));
        max = Math.max(max, Math.abs(ss));
        max = Math.max(max, Math.abs(sd));

        if (max > 1.0) {
            fs /= max;
            fd /= max;
            ss /= max;
            sd /= max;
        }

        if(gamepad1.b) {
            fs /= 2; fd /= 2;
            ss /= 2; sd /= 2;
        }

        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);
    }

    private boolean straightMove() {//miscare din dpad
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

        fs = y + x; fd = y - x;
        ss = y - x; sd = y + x;

        if(fs == 0 && fd == 0 && ss == 0 && sd == 0)//daca nu sa apasat nimic pe dpad returneaza fals
            return false;

        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);

        return true;
    }
}