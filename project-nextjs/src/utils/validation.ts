function isValidEmail(email: string): boolean {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
    return emailRegex.test(email)
  }
  
  function isValidUUID(uuid: string): boolean {
    const uuidRegex = /^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/i
    return uuidRegex.test(uuid)
  }
  
  function isValidStringLength(str: string, min: number, max: number): boolean {
    return str.length >= min && str.length <= max
  }
  
  export function validateUserData(data: any): { isValid: boolean; errors: string[] } {
    const errors: string[] = []
  
    if (!data.name || !isValidStringLength(data.name, 2, 64)) {
      errors.push('Имя должно быть от 2 до 64 символов')
    }
  
    if (!data.email || !isValidEmail(data.email)) {
      errors.push('Некорректный email')
    }
  
    if (!data.gender || !['MALE', 'FEMALE', 'TIEBANAT'].includes(data.gender)) {
      errors.push('Некорректный пол')
    }
  
    if (data.description && !isValidStringLength(data.description, 0, 500)) {
      errors.push('Описание максимум 500 символов')
    }
  
    return {
      isValid: errors.length === 0,
      errors
    }
  }
  
  export function validateCoupleData(data: any): { isValid: boolean; errors: string[] } {
    const errors: string[] = []
  
    if (!data.user_one_id || !isValidUUID(data.user_one_id)) {
      errors.push('Некорректный ID первого пользователя')
    }
  
    if (!data.user_two_id || !isValidUUID(data.user_two_id)) {
      errors.push('Некорректный ID второго пользователя')
    }
  
    if (data.user_one_id === data.user_two_id) {
      errors.push('Нельзя создать пару с самим собой')
    }
  
    return {
      isValid: errors.length === 0,
      errors
    }
  }