robot_path = 'src/main/java/frc/robot/'
subsystem_class = '''package frc.robot.subsystems;

import com.chopshop166.chopshoplib.commands.SmartSubsystemBase;

import frc.robot.maps.subsystems.${CLASSNAME}Map;

public class ${CLASSNAME} extends SmartSubsystemBase {

    private ${CLASSNAME}Map map;

    public ${CLASSNAME}(${CLASSNAME}Map map) {
        this.map = map;
    } 

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

with open(f'{robot_path}maps/RobotMap.java','r') as fp:
    robot_map = fp.read()

robot_map = robot_map.split('Maps')
robot_map = f'{robot_map[0]}Maps\n    private {class_name}Map {instance_name}Map = new {class_name}Map();\n{robot_map[1]}'

robot_map = robot_map.split('package frc.robot.maps;')
robot_map = f'package frc.robot.maps;\nimport frc.robot.maps.subsystems.{class_name}Map;\n{robot_map[-1]}'

robot_map = robot_map.split('Getters')
robot_map = f'{robot_map[0]}Getters\n    public {class_name}Map get{class_name}Map() {{\n        return {instance_name}Map;\n    }} \n{robot_map[1]}'


with open(f'{robot_path}maps/RobotMap.java','w') as fp:
    fp.write(robot_map)