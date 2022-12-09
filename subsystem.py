robot_path = 'src/main/java/frc/robot/'
subsystem_class = '''package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;

public class ${CLASSNAME} extends SmartSubsystemBase {

    @Override
    public void reset() {
        // Nothing to reset here
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
        // Use this for any background processing
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    @Override
    public void safeState() {

    }
}'''

subsystem_map = '''package frc.robot.maps.subsystems;

public class ${CLASSNAME}Map {
    public ${CLASSNAME}Map() {

    }
}
'''

class_name = input('Subsystem Class Name: ')
instance_name = class_name[0].lower() + class_name[1:]

with open(f'{robot_path}subsystems/{class_name}.java', 'w') as fp:
    fp.write(subsystem_class.replace('${CLASSNAME}',class_name))

with open(f'{robot_path}Robot.java','r') as fp:
    robot_class = fp.read()

robot_class = robot_class.split('Subsystems')
robot_class = f'{robot_class[0]}Subsystems\n    {class_name} {instance_name} = new {class_name}(map.get{class_name}Map());\n{robot_class[1]}'

robot_class = robot_class.split('public class Robot')

robot_class = f'{robot_class[0]}import frc.robot.subsystems.{class_name};\npublic class Robot{robot_class[1]}'

with open(f'{robot_path}Robot.java','w') as fp:
    fp.write(robot_class)

with open(f'{robot_path}maps/subsystems/{class_name}Map.java','w') as fp:
    fp.write(subsystem_map.replace('${CLASSNAME}',class_name))
