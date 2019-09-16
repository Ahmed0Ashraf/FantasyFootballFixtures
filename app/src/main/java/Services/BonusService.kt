package Services

object BonusService {

    var playersBonus = 0
    var weeklyPoints = 0

    var rivalBonus = 0
    var rivalWeeklyPoints = 0
fun calculateUserFinishedBonus(){
        for (x in 0 until UserService.myPlayers.size){
         var playerId= UserService.myPlayers[x].id
            for (y in 0 until LiveService.weekBonus.size){
                if (playerId == LiveService.weekBonus[y].id){
                    playersBonus += LiveService.weekBonus[y].bonus
                }
            }
        }
    weeklyPoints = UserService.points - playersBonus

}
    fun calculateUserProvisionedBonus(){
        for (x in 0 until UserService.myPlayers.size){
            var playerId= UserService.myPlayers[x].id
            for (y in 0 until LiveService.weekBonus.size){
                if (playerId == LiveService.weekBonus[y].id){
                    if (!LiveService.weekBonus[y].confirmed){
                        playersBonus += LiveService.weekBonus[y].bonus
                    }
                }
            }
        }
        weeklyPoints = UserService.points - playersBonus

    }
    fun calculateRivalFinishedBonus(){
        rivalBonus = 0
        for (x in 0 until RivalsService.rivalPlayers.size){
            var playerId= RivalsService.rivalPlayers[x].id
            for (y in 0 until LiveService.weekBonus.size){
                if (playerId == LiveService.weekBonus[y].id){
                    rivalBonus += LiveService.weekBonus[y].bonus
                }
            }
        }
        rivalWeeklyPoints = RivalsService.rivalPoints - rivalBonus

    }
    fun calculateRivalProvisionedBonus(){
        rivalBonus = 0
        for (x in 0 until RivalsService.rivalPlayers.size){
            var playerId= RivalsService.rivalPlayers[x].id
            for (y in 0 until LiveService.weekBonus.size){
                if (playerId == LiveService.weekBonus[y].id){
                    if (!LiveService.weekBonus[y].confirmed){
                        rivalBonus += LiveService.weekBonus[y].bonus
                    }
                }
            }
        }
        rivalWeeklyPoints = RivalsService.rivalPoints - rivalBonus

    }
}