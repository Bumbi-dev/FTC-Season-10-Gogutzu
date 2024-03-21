package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

@TeleOp(name = "✈️GAMEPLAY✈️", group = "Bubu")
public class PleacaCodu extends RobotHardware {

    boolean sePrinde = false;
    double yaw;
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

            yaw = imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS);
            if(gamepad1.start)
                imu.resetYaw();

            telemetry.addLine("" + imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) + '\n');

            telemetry.addLine(Math.cos(yaw) + " " + Math.cos(yaw));
            telemetry.addLine(Math.sin(yaw) + " " + Math.sin(yaw));
            telemetry.update();

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
        else if(motorBrat.getMode() == DcMotor.RunMode.RUN_WITHOUT_ENCODER) {
            motorBrat.setPower(gamepad2.left_stick_y);
        }

        if(gamepad2.start) {
            motorBrat.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorBrat.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }

        if(gamepad2.dpad_up)
            ArmToPosition(ArmPositions.BACK_BOARD_POSITION);
        if(gamepad2.dpad_down)
            ArmToPosition(ArmPositions.HOVER_POSITION);
        if(gamepad2.dpad_left)
            ArmToPosition(ArmPositions.AIRPLANE);
        if(gamepad2.dpad_right)
            ArmToPosition(ArmPositions.AIRPLANE2);



        //telemetry.addLine(motorBrat.getCurrentPosition() + "");
        //telemetry.update();
    }
    private void freeFall() {
        if(gamepad2.b) {
            if(motorBrat.getZeroPowerBehavior() == DcMotor.ZeroPowerBehavior.BRAKE)
                motorBrat.setPower(0.2);
            motorBrat.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        }
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

        if(gamepad2.a)
            setServosPosition(ServoPositions.CLOSE);

        /*_____  9/11  _____*/
        if(gamepad2.ps)
            if(gamepad2.back)
                diana.setPosition(1);
            else
                openCapcana();

        if(gamepad1.x)
            closeCapcana();

        if(gamepad1.y)
            diana.setPosition(0.5f);
    }

    private void sergiuMovevement() {//miscare din joystick
        float y;
        float x;
        float fd, fs, sd, ss;
        float rotatie;

        y = gamepad1.right_trigger - gamepad1.left_trigger;
        x = gamepad1.left_stick_x;

        rotatie = gamepad1.right_stick_x;



        fs = y + x; fd = y - x;
        ss = y - x; sd = y + x;

        fs += rotatie;   fd -= rotatie;
        ss += rotatie;   sd -= rotatie;

        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);
    }

    private boolean straightMove() {//miscare din dpad
        //Navigation mode
        float y = 0;
        float x = 0;
        float fd, fs, sd, ss;

        if(gamepad1.dpad_up)
            y = 1;

        if(gamepad1.dpad_down)
            y -= 1;

        if(gamepad1.dpad_right)
            x = 1;

        if(gamepad1.dpad_left)
            x -= 1;

        if(y == 1 || y == -1) {
            x = (float) Math.sin(yaw) * y;
            y = (float) Math.cos(yaw) * y;
        }
        else if(x == 1 || x == -1) {
            y = -(float)Math.sin(yaw) * x;
            x = (float)Math.cos(yaw)  * x;
        }

        fs = y + x; fd = y - x;
        ss = y - x; sd = y + x;

        if(fs == 0 && fd == 0 && ss == 0 && sd == 0)//daca nu sa apasat nimic pe dpad returneaza fals
            return false;

        //if(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) >= 0 && imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) <= 90) {
        //    fs *= Math.cos(yaw);
        //    sd *= Math.cos(yaw);
        //    ss *= -Math.cos(yaw);
        //    fd *= -Math.cos(yaw);
        //}
        //if(imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) < 0 && imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.DEGREES) >= -90) {
        //    fs *= -Math.cos(yaw);
        //    sd *= -Math.cos(yaw);
        //    ss *= Math.cos(yaw);
        //    fd *= Math.cos(yaw);
        //}

        motorFS.setPower(fs);  motorFD.setPower(fd);
        motorSS.setPower(ss);  motorSD.setPower(sd);

        return true;
    }
}