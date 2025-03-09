package dev.thebjoredcraft.ultimatemoderation.auth

data class AuthAccount (
    var username: String,
    var password: String,
    var blocked: Boolean
) {
    fun edit(block: AuthAccount.() -> Unit): AuthAccount {
        AuthController.accounts.remove(this)
        block()
        AuthController.accounts.add(this)
        return this
    }
}