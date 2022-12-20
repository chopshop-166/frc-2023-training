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

def insert_into(filename: str, keyword: str, line:str, before:bool = False):
    with open(f'{robot_path}{filename}','r') as fp:
        contents = fp.read()
    prev, after = contents.split(keyword)
    if before:
        new_contents = f'{prev}\n{line}\n{keyword}{after}'
    else:
        new_contents = f'{prev}{keyword}\n{line}\n{after}'
    with open(filename,'w') as fp:
        fp.write(new_contents)

def save_template(filename, template, **kwargs):
    new_template = template
    for key, value in kwargs.items():
        new_template = new_template.replace(f'${{{key}}}',value)

    with open(f'{robot_path}{filename}','w') as fp:
        fp.write(new_template)

class_name = input('Subsystem Class Name: ')
instance_name = class_name[0].lower() + class_name[1:]


save_template(f'subsystems/{class_name}.java',subsystem_class,CLASSNAME=class_name)

insert_into(f'Robot.java', 'Subsystems', f'    {class_name} {instance_name} = new {class_name}(map.get{class_name}Map());')

insert_into(f'Robot.java', 'public class Robot', f'import frc.robot.subsystems.{class_name};', True)



save_template(f'maps/subsystems/{class_name}Map.java', subsystem_map, CLASSNAME=class_name)


insert_into(f'maps/RobotMap.java', 'Maps',
f'    private {class_name}Map {instance_name}Map = new {class_name}Map();')

insert_into(f'maps/RobotMap.java', 'Getters',
f'    public {class_name}Map get{class_name}Map() {{\n        return {instance_name}Map;\n    }}')

insert_into(f'maps/RobotMap.java', 'package frc.robot.maps;',
f'import frc.robot.maps.subsystems.{class_name}Map;')

    
