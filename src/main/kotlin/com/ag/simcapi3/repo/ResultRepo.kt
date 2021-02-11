package com.ag.simcapi3.repo

import com.ag.simcapi3.model.SimResult
import org.springframework.data.repository.CrudRepository

interface ResultRepo: CrudRepository<SimResult, String> {
}