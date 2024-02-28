import Config from '@/settings'

const TokenKey = Config.TokenKey

export function getToken() {
  return localStorage.getItem(TokenKey)
}

export function setToken(token, rememberMe) {
  if (rememberMe) {
    return localStorage.setItem(TokenKey, token)
  } else return localStorage.setItem(TokenKey, token)
}

export function removeToken() {
  return localStorage.removeItem(TokenKey)
}
