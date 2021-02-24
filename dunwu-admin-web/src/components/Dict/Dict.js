import Vue from 'vue'
import { getDicts } from '@/api/system/dict'

export default class Dict {
  constructor(dict) {
    this.dict = dict
  }

  async init(codes, completeCallback) {
    if (codes === undefined || codes === null) {
      throw new Error('need Dict codes')
    }
    const ps = []
    codes.forEach(code => {
      Vue.set(this.dict.label, code, {})
      Vue.set(this.dict, code, [])
      ps.push(
        getDicts(code).then(data => {
          const expectDict = data.content[0]
          this.dict[code] = expectDict
          expectDict.options.forEach(o => {
            // Vue.set(this.dict.dict[code], o.code, o)
            Vue.set(this.dict.label[code], o.code, o.name)
          })
        })
      )
    })
    // console.log('字典', this.dict)
    await Promise.all(ps)
    completeCallback()
  }
}
