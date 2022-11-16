package frc.robot.util;

import java.util.HashMap;

public class TagField {

    private final HashMap<Integer, AprilTag> tags = new HashMap<Integer, AprilTag>();

    public TagField(AprilTag... tags) {
        for (AprilTag tag : tags) {
            this.tags.put(tag.getId(), tag);
        }
    }

    public AprilTag getTag(int id) {
        return tags.get(id);
    }

}
