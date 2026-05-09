import request from './request'

export const listDispenses = () => request.get('/api/dispenses')
export const dispense = (id) => request.post(`/api/dispenses/${id}/dispense`)
