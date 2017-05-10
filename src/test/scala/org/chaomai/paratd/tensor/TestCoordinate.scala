package org.chaomai.paratd.tensor

import org.chaomai.paratd.UnitSpec

/**
  * Created by chaomai on 08/05/2017.
  */
class TestCoordinate extends UnitSpec {
  "A Coordinate" should "get coordinate without some dimension" in {
    val c = Coordinate(IndexedSeq(0, 1, 2, 3, 4, 5, 6))
    assert(c.dimWithout(3, 4).coordinate == IndexedSeq(0, 1, 2, 5, 6))
  }

  it should "get coordinate with some dimension kept" in {
    val c = Coordinate(IndexedSeq(0, 1, 2, 3, 4, 5, 6))
    assert(c.dimKept(3, 4).coordinate == IndexedSeq(3, 4))
  }

  it should "compose with indexes" in {
    val c = Coordinate(IndexedSeq(0, 1, 2, 3))
    assert(c.compose(4, 5, 6).coordinate == IndexedSeq(0, 1, 2, 3, 4, 5, 6))
  }
}