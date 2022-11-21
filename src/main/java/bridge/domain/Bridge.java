package bridge.domain;

import bridge.util.Constant;

import java.util.List;

public class Bridge {

    private final List<String> bridge;

    public Bridge(List<String> bridge) {
        this.bridge = bridge;
    }

    public int getBridgeLength() {
        return bridge.size();
    }

    public String getBridgeByIndex(int index) {
        return bridge.get(index);
    }

    public String getLastOfBridge() {
        return bridge.get(getBridgeLength() - Constant.GAP_IN_POSITION_AND_SIZE);
    }

}
