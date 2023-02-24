package com.github.clockworkclyde.domain.validate

import com.github.clockworkclyde.data.sources.ResourcesProvider
import com.github.clockworkclyde.domain.IUseCase
import com.github.clockworkclyde.domain.R
import javax.inject.Inject

class ValidateFieldIsNotEmptyUseCase @Inject constructor(
   private val resources: ResourcesProvider
): IValidateFieldIsNotEmptyUseCase {

   override suspend fun invoke(param: String): String? {
      return when {
         param.isEmpty() -> resources.getString(R.string.empty_input_error)
         else -> null
      }
   }
}

interface IValidateFieldIsNotEmptyUseCase: IUseCase.SingleInOut<String, String?>