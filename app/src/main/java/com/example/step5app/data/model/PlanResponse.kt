package com.example.step5app.data.model

import com.example.step5app.domain.model.Plan

data class PlanResponse(
    val message: String,
    val data: List<Plan>
)

data class PlanDetailsResponse(
    val message: String,
    val data: Plan
)