import Vue from 'vue'
import { getDicts } from '@/api/system/dict'

export default class Dict {
  constructor(dict) {
    this.dict = dict
  }

  async init(codes, completeCallback) {
    if (codes === undefined || name === null) {
      throw new Error('need Dict names')
    }
    const ps = []
    codes.forEach(code => {
      Vue.set(this.dict.dict, code, {})
      Vue.set(this.dict.label, code, {})
      Vue.set(this.dict, code, [])
      ps.push(
        getDicts(code).then(data => {
          const dicts = data.content
          console.log('code', code)
          console.log('dicts', dicts)

          this.dict[code].splice(0, 0, ...dicts)
          dicts.forEach(d => {
            d.options.forEach(o => {
              Vue.set(this.dict.dict[code], o.code, o)
              Vue.set(this.dict.label[code], o.code, o.name)
            })
          })
          console.log('this.dict', this.dict)
        })
      )
    })
    console.log('字典', this.dict)
    await Promise.all(ps)
    completeCallback()
  }
}
