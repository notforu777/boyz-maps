fun main (args: Array<String>){
    val s : String? =readLine()
    println(calculateWordStat(s!!))
}

fun calculateWordStat(input:String): String{
    var list : MutableList<String> = mutableListOf()
    for (a in input.split(" ")) {
        list.add(a)
    }
    var words: MutableMap<String, Int> = mutableMapOf()
    var mx = 0
    var ans ="0"
    for (i in list)
    {
        if (words.containsKey(i)) {
            words[i] = words[i]!! + 1
            if (words[i]!! > mx) {
                mx = words[i]!!
                ans = i
            }
        }
        else
            words[i] = 1
    }
    return ans
}

