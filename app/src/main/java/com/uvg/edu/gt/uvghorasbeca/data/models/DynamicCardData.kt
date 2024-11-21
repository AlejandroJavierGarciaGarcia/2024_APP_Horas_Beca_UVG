data class Task(
    var id: Int = 0,
    var title: String = "",
    var location: String = "",
    var date: String = "",
    var startTime: String? = null,
    var endTime: String? = null,
    var totalHoursCompleted: Float? = null,
    var isRecurring: Boolean = false,
    var recurrencePattern: String? = null,
    var currentParticipants: Int = 0,
    var maxParticipants: Int = 0,
    var rating: Int = 0,
    var remainingHours: Long? = null,
    var info: String? = null
) {
    // Default no-argument constructor for Firestore
    constructor() : this(0, "", "", "", null, null, null, false, null, 0, 0, 0, null, null)
}
