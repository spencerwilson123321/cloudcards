interface test {
    fun getCards() {
        val exactName: String = "thunder drake"
        val pageSize: Int = 2
        val page: Int = 1

        val cardsResponse: Response<List<MtgCard>> = MtgCardApiClient.getCardsByExactName(exactName, pageSize, page)
        val cards = cardsResponse.body()

    }  // will be default in the Java interface

}