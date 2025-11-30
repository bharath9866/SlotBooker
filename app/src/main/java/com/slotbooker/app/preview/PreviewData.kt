package com.slotbooker.app.preview

import com.slotbooker.app.domain.model.Slot

object PreviewData {

    val Slot1 = Slot(
        slotId = "SLOT001",
        name = "Morning Yoga",
        startTime = "09:00",
        endTime = "10:00"
    )

    val Slot2 = Slot(
        slotId = "SLOT002",
        name = "Late Morning Slot",
        startTime = "10:00",
        endTime = "11:00"
    )

    val Slot3 = Slot(
        slotId = "SLOT003",
        name = "Afternoon Slot",
        startTime = "14:00",
        endTime = "15:00"
    )

    val Slot4 = Slot(
        slotId = "SLOT004",
        name = "Evening Slot",
        startTime = "16:00",
        endTime = "17:00"
    )

    val AllSlots = listOf(
        Slot1, Slot2, Slot3, Slot4
    )

    val EmptySlots = emptyList<Slot>()
}