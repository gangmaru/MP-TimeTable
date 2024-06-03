package androidtown.org.listener;

import androidtown.org.data.type.DataType;

public interface WebDataListener {
    void receive(String data, DataType type);
}
