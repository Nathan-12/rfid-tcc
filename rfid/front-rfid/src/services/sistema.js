import { http } from './config'

export default	{

	salvar:(atividade)=>{
		return http.post('adicionar', atividade);
  },
}