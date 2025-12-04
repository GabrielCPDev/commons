package utils

class ResourceNotFoundException(message: String) : RuntimeException(message)
class InvalidQuantityException(message: String) : RuntimeException(message)
class DuplicateResourceException(message: String) : RuntimeException(message)
class TenantLimitExceededException(message: String) : RuntimeException(message)
class InvalidApiKeyException(message: String) : RuntimeException(message)
class InstanceLimitExceededException(message: String) : RuntimeException(message)
class UnauthorizedException(message: String) : RuntimeException(message)
class ForbiddenException(message: String) : RuntimeException(message)
class LimitExceededException(message: String) : RuntimeException(message)
class AuthenticationException(message: String) : RuntimeException(message)