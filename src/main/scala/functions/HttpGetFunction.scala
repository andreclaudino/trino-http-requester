package com.d2x.trinoplugins
package functions

import io.airlift.slice.{Slice, Slices}
import io.trino.plugin.base.util.JsonTypeUtil
import io.trino.spi.`type`.{StandardTypes, Type}
import io.trino.spi.block.{Block, SingleMapBlock}
import io.trino.spi.function._
import scalaj.http._


@ScalarFunction(value = "http_get", deterministic = true)
@Description("Returns the result of an Http Get request")
object HttpGetFunction {

  @TypeParameter(value = "KP")
  @TypeParameter(value = "VP")
  @TypeParameter(value = "KH")
  @TypeParameter(value = "VH")
  @SqlType(StandardTypes.JSON)
  def httpGetFromArrayMap(
                           @TypeParameter("KP") paramKeyType: Type,
                           @TypeParameter("KH") headerKeyType: Type,
                           @SqlType(StandardTypes.VARCHAR) httpAddress: Slice,
                           @SqlType("map(KP,VP)") parameters: Block,
                           @SqlNullable @SqlType("map(KH,VH)") headers: Block,
  ): Slice = {

    val stringParams = castSliceMap(paramKeyType, parameters)
    val stringHeaders = castSliceMap(headerKeyType, headers)
    val request = Http(httpAddress.toStringUtf8).headers(stringHeaders).params(stringParams)
    val stringResponse = request.asString.body
    val sliceResponse = Slices.utf8Slice(stringResponse)

    println(sliceResponse)

    JsonTypeUtil.jsonParse(sliceResponse)
  }

  private def castSliceMap(keyType: Type, block: Block): Map[String, String] = {
    val mapBlock = block.asInstanceOf[SingleMapBlock]

    (0 until block.getPositionCount by 2).map((index) => {
      val keyIndex = index
      val valueIndex = index+1

//      val key = mapBlock.getObject(keyIndex, keyType.getJavaType)
//      val value = mapBlock.getObject(valueIndex, keyType.getJavaType)

      val keyLenght = mapBlock.getSliceLength(keyIndex)
      val key = mapBlock.getSlice(keyIndex, 0, keyLenght).toStringUtf8

      val valueLength = mapBlock.getSliceLength(valueIndex)
      val value = mapBlock.getSlice(valueIndex, 0, valueLength).toStringUtf8

      (key, value)
    }).toMap
  }
}

