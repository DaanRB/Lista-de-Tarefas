package Daniel.personal.todoList.ui.tarefas.tarefasInfo

import androidx.appcompat.app.AlertDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import Daniel.personal.todoList.R
import Daniel.personal.todoList.repository.TarefaDatabase
import Daniel.personal.todoList.repository.TarefaEntity
import kotlinx.coroutines.launch

class TarefaInfoViewModel(val context: Context): ViewModel(){
    val tarefaDAO = TarefaDatabase.getDatabase(context)
    val carregar = MutableLiveData<TarefaEntity>()
    val excluir = MutableLiveData<Int>()

    fun carregarTarefa(id: Int){
        viewModelScope.launch{
            carregar.value = tarefaDAO.getOne(id)
        }
    }

    fun excluirTarefa(tarefa: TarefaEntity){
        AlertDialog.Builder(context, R.style.alertDialogStyle)
            .setTitle(R.string.alert_dialog_excluir_titulo)
            .setMessage(R.string.alert_dialog_excluir_mensagem)
            .setPositiveButton(R.string.alert_dialog_positive){dialog, which ->
                viewModelScope.launch{
                    excluir.value = tarefaDAO.delete(tarefa)
                }
            }
            .setNeutralButton(R.string.alert_dialog_neutral, null).show()
    }
}