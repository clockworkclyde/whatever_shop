package com.github.clockworkclyde.domain.usecases.validate

import com.github.clockworkclyde.core.common.IResourcesProvider
import com.github.clockworkclyde.domain.R
import com.github.clockworkclyde.domain.usecases.IUseCase
import javax.inject.Inject

class ValidateFieldIsNotEmptyUseCase @Inject constructor(
   private val resources: IResourcesProvider
): IValidateFieldIsNotEmptyUseCase {

   override suspend fun invoke(param: String): String? {
      return when {
         param.isEmpty() -> resources.getString(R.string.empty_input_error)
         else -> null
      }
   }
}

interface IValidateFieldIsNotEmptyUseCase: IUseCase.SingleInOut<String, String?>