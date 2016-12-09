package org.tj.xword


object XWord {
  val gridSize = 3                                //> gridSize  : Int = 3
  case class Coord(val x: Int, y: Int) {
    def /\ = Coord(x, y - 1)
    def \/ = Coord (x, y + 1)
    def |> = Coord(x + 1, y)
    def <| = Coord(x - 1, y)
    def -/ = Coord(x + 1, y - 1)
    def -\ = Coord(x + 1, y + 1)
    def \- = Coord(x - 1, y - 1)
    def /- = Coord(x - 1, y + 1)
    def isValid = x >= 0 && x < gridSize && y >= 0 && y < gridSize
  }
  type Path = List[Coord]
  type Paths = List[Path]
  
  def /\/\ (c: Coord) = List(c /\, c \/, c |>, c <|, c -/, c -\, c \-, c /-).filter(_.isValid)
  
  val dict = Set("MAN", "ARM", "CAR")             //> dict  : scala.collection.immutable.Set[String] = Set(MAN, ARM, CAR)
  val dictMaxLength = dict.map(w => w.size).max   //> dictMaxLength  : Int = 3
  
  def allPaths(from: Coord, to: Coord): Paths = {
    def allPathsRec(currentCoord: Coord, currentPath: Path): Paths = {
      if (currentPath.size > dictMaxLength) Nil
      else if (currentCoord == to) List(currentPath)
      else /\/\(currentCoord).filter(c => !(currentPath contains c)).flatMap(c => allPathsRec(c, c :: currentPath))
    }
    allPathsRec(from, List(from)).map(_.reverse)
  }    
  
  val dictMap = Map(Coord(0, 0) -> 'M', Coord(1, 0) -> 'B', Coord(2, 0) -> 'C',
                    Coord(0, 1) -> 'A', Coord(1, 1) -> 'R', Coord(2, 1) -> 'A',
                    Coord(0, 2) -> 'N', Coord(1, 2) -> 'M', Coord(2, 2) -> 'O')  
  
  def fromsTos: List[(Coord, Coord)] = {
    val keys = dictMap.keySet.toList
    for {
      c1 <- keys
      c2 <- keys.filter(_ != c1)
    } yield (c1, c2)
  }
  
  def main(args: Array[String]): Unit = {
    // Generates all possible pairs path in the grid
    // List(List(Coord(0,2), Coord(0,1), Coord(0,0)),  ...
    val allPairsPaths = fromsTos.map(t => allPaths(t._1, t._2)).flatten 
    
    // Generates all words from the pairs path
    // List(NAM, NRM, NRC, NAR, NMR, NR, NMO ....
    val words = allPairsPaths.map(p => p.map(c => dictMap(c))).map(cs => cs.mkString)
    // Combine the two lists, keep only what is in the dictionary
    // List((MAN,List(Coord(0,0), Coord(0,1), Coord(0,2))), (CAR,List(Coord(2,0), Coord(2,1), Coord(1,1))) ....

    val wordsPath = (words, allPairsPaths).zipped.map((_, _)).filter(t => dict contains t._1)
    // Pretty print
    wordsPath.foreach(wp => {println(s"Word ${wp._1} has path ${wp._2}")})
  }
}