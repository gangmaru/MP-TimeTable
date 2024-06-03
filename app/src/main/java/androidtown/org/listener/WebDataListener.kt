package androidtown.org.listener

import androidtown.org.data.type.DataType

interface WebDataListener {
    fun receive(data: String?, type: DataType?)
}
